package com.study.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BATCH_STEP_EXECUTION")
public class BatchStepExecution {
    @Id
    @Column(name = "STEP_EXECUTION_ID")
    private Long stepExecutionId;
    @Column(name = "VERSION")
    private Long version;
    @Column(name = "STEP_NAME")
    private String stepName;
    @Column(name = "JOB_EXECUTION_ID")
    private Long jobExecutionId;
    @Column(name = "START_TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startTime;
    @Column(name = "END_TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endTime;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "COMMIT_COUNT")
    private Long commitCount;
    @Column(name = "READ_COUNT")
    private Long readCount;
    @Column(name = "FILTER_COUNT")
    private Long filterCount;
    @Column(name = "WRITE_COUNT")
    private Long writeCount;
    @Column(name = "READ_SKIP_COUNT")
    private Long readSkipCount;
    @Column(name = "WRITE_SKIP_COUNT")
    private Long writeSkipCount;
    @Column(name = "PROCESS_SKIP_COUNT")
    private Long processSkipCount;
    @Column(name = "ROLLBACK_COUNT")
    private Long rollbackCount;
    @Column(name = "EXIT_CODE")
    private String exitCode;
    @Column(name = "EXIT_MESSAGE")
    private String exitMessage;
    @Column(name = "LAST_UPDATED")
    private LocalDateTime lastUpdated;
}
