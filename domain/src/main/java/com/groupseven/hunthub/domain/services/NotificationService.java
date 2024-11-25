package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.repository.NotificationRepository;

import com.groupseven.hunthub.domain.interfaces.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    private final List<Observer> observers = new ArrayList<>();

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void notifyObserver(Observer observer, String message, String theme, Task task) {
        Notification notification = null;

        if (observer instanceof PO po) {
            notification = new Notification(message, theme, po, task);
        } else if (observer instanceof Hunter hunter) {
            notification = new Notification(message, theme, hunter, task);
        } else {
            throw new IllegalArgumentException("Tipo de observador desconhecido");
        }

        System.out.println(notification);
        notificationRepository.save(notification);

        observer.update(message, theme, task);
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyAllObservers(String message, String theme, Task task) {
        for (Observer observer : observers) {
            notifyObserver(observer, message, theme, task);
        }
    }

    public boolean NotifyPO(PO po, String task, String message) {
        Notification notification = new Notification(message, task, po, null);
        notificationRepository.save(notification);
        return true;
    }

    public boolean NotifyHunter(Hunter hunter, String task, String message) {
        Notification notification = new Notification(message, task, hunter, null);
        notificationRepository.save(notification);
        return true;
    }
}
