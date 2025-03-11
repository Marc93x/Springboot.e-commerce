package com.example.ecommerce.controller;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderStatus;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Ajoute cette annotation pour que Spring reconnaisse ce contrôleur
@RequestMapping("/orders")  // Spécifie le chemin de base pour toutes les routes
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // Récupérer toutes les commandes
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Créer une nouvelle commande
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    // Mettre à jour le statut d'une commande
    @PutMapping("/{orderId}")
    public void updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    // Supprimer une commande
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
