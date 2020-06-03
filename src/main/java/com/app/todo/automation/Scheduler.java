package com.app.todo.automation;

import com.app.todo.entity.Todo;
import com.app.todo.entity.User;
import com.app.todo.repository.TodoRepository;
import com.app.todo.repository.UserRepository;
import com.app.todo.service.MailService;
import org.joda.time.DateTimeComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    private final TodoRepository todoRepository;

    private final UserRepository userRepository;

    private final MailService mailService;

    public Scheduler(@Qualifier("todoRepository") TodoRepository todoRepository, @Qualifier("userRepository") UserRepository userRepository, MailService mailService) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

//     @Scheduled(cron = "0 0 0 * * *", zone = "Indian/Maldives")
    
//     @Scheduled(cron = "0 0/2 * * * ?")
    @Scheduled(cron = "0 0 0/6 * * ?")
    public void cornJob() throws Exception {
        LOGGER.info("Job Started At {}", new Date());
        process(this.todoRepository.findAll());
        LOGGER.info("Job Ended At {}", new Date());
    }

    private void process(List<Todo> todos) {
        if (!todos.isEmpty()) {
            Map<String, List<Todo>> result = todos.parallelStream()
                    .collect(Collectors.groupingBy(Todo::getCreatedBy));
            result.forEach((key, value) -> {
                User user = this.userRepository.findByUsernameOrEmail(key, key)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                String email = user.getEmail();
                List<Todo> overDueItems = filterOverdueItem(value);
                if (!overDueItems.isEmpty()) {
                    try {
                        updateStatus(overDueItems);
                        sendOverdueMail(email, overDueItems);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            LOGGER.info("No overdue items found");
        }
    }

    private void updateStatus(List<Todo> todoList) {
        try {
            todoList = todoList.parallelStream().filter(e -> !e.getStatus().equals("Overdue"))
                    .collect(Collectors.toList());
            if (!todoList.isEmpty()) {
                for (Todo todo : todoList) {
                    todo.setStatus("Overdue");
                    this.todoRepository.saveAndFlush(todo);
                }
            }
        } catch (Exception e) {
            LOGGER.info("Error while updating todo status");
        }
    }

    private List<Todo> filterOverdueItem(List<Todo> items) {
        return items.parallelStream().filter(e -> compareDate(e.getDuedate()))
                .collect(Collectors.toList());
    }

    private void sendOverdueMail(String email, List<Todo> todos) throws MessagingException {
        LOGGER.info("Overdue Mail Process Started at {}", new Date());
        this.mailService.sendOverdueMail(email, todos);
    }

    private boolean compareDate(Date overdueDate) {
        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
        int val = dateTimeComparator.compare(overdueDate, new Date());
        return val < 0;
    }

}
