package com.snacktrack.snacktrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.snacktrack.snacktrack.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
