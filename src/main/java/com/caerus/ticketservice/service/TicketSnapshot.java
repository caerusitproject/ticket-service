package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Ticket;

public record TicketSnapshot(
    String status,
    String priority,
    String item,
    String notificationMode,
    String impact,
    String groupName,
    String site,
    String technician,
    String subject,
    String requester,
    String assigneeUserId,
    String categoryId,
    String subcategoryId,
    String assetId,
    String startDate,
    String endDate,
    String dueDate) {
  static TicketSnapshot from(Ticket t) {
    return new TicketSnapshot(
        enumToString(t.getStatus()),
        enumToString(t.getPriority()),
        t.getItem(),
        t.getNotificationMode(),
        t.getImpact(),
        t.getGroupName(),
        t.getSite(),
        t.getTechnician(),
        t.getSubject(),
        t.getRequester(),
        t.getAssigneeUserId(),
        toId(t.getCategory()),
        toId(t.getSubcategory()),
        toId(t.getAsset()),
        toInstant(t.getStartDate()),
        toInstant(t.getEndDate()),
        toInstant(t.getDueDate()));
  }

  private static String enumToString(Enum<?> e) {
    return e != null ? e.name() : null;
  }

  private static String toId(Object obj) {
    try {
      return obj != null ? String.valueOf(obj.getClass().getMethod("getId").invoke(obj)) : null;
    } catch (Exception ignored) {
      return null;
    }
  }

  private static String toInstant(java.time.Instant i) {
    return i != null ? i.toString() : null;
  }
}
