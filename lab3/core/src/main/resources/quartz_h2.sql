-- Thanks to Amir Kibbar and Peter Rietzler for contributing the schema for H2 database,
-- and verifying that it works with Quartz's StdJDBCDelegate
--
-- Note, Quartz depends on row-level locking which means you must use the MVCC=TRUE
-- setting on your H2 database, or you will experience dead-locks
--
--
-- In your Quartz properties file, you'll need to set
-- org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate

CREATE TABLE IF NOT EXISTS QUARTZ_CALENDARS (
                                SCHED_NAME VARCHAR(120) NOT NULL,
                                CALENDAR_NAME VARCHAR (200)  NOT NULL ,
                                CALENDAR IMAGE NOT NULL
);

CREATE TABLE IF NOT EXISTS QUARTZ_CRON_TRIGGERS (
                                    SCHED_NAME VARCHAR(120) NOT NULL,
                                    TRIGGER_NAME VARCHAR (200)  NOT NULL ,
                                    TRIGGER_GROUP VARCHAR (200)  NOT NULL ,
                                    CRON_EXPRESSION VARCHAR (120)  NOT NULL ,
                                    TIME_ZONE_ID VARCHAR (80)
);

CREATE TABLE IF NOT EXISTS QUARTZ_FIRED_TRIGGERS (
                                     SCHED_NAME VARCHAR(120) NOT NULL,
                                     ENTRY_ID VARCHAR (95)  NOT NULL ,
                                     TRIGGER_NAME VARCHAR (200)  NOT NULL ,
                                     TRIGGER_GROUP VARCHAR (200)  NOT NULL ,
                                     INSTANCE_NAME VARCHAR (200)  NOT NULL ,
                                     FIRED_TIME BIGINT NOT NULL ,
                                     SCHED_TIME BIGINT NOT NULL ,
                                     PRIORITY INTEGER NOT NULL ,
                                     STATE VARCHAR (16)  NOT NULL,
                                     JOB_NAME VARCHAR (200)  NULL ,
                                     JOB_GROUP VARCHAR (200)  NULL ,
                                     IS_NONCONCURRENT BOOLEAN  NULL ,
                                     REQUESTS_RECOVERY BOOLEAN  NULL
);

CREATE TABLE IF NOT EXISTS QUARTZ_PAUSED_TRIGGER_GRPS (
                                          SCHED_NAME VARCHAR(120) NOT NULL,
                                          TRIGGER_GROUP VARCHAR (200)  NOT NULL
);

CREATE TABLE IF NOT EXISTS QUARTZ_SCHEDULER_STATE (
                                      SCHED_NAME VARCHAR(120) NOT NULL,
                                      INSTANCE_NAME VARCHAR (200)  NOT NULL ,
                                      LAST_CHECKIN_TIME BIGINT NOT NULL ,
                                      CHECKIN_INTERVAL BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS QUARTZ_LOCKS (
                            SCHED_NAME VARCHAR(120) NOT NULL,
                            LOCK_NAME VARCHAR (40)  NOT NULL
);

CREATE TABLE IF NOT EXISTS QUARTZ_JOB_DETAILS (
                                  SCHED_NAME VARCHAR(120) NOT NULL,
                                  JOB_NAME VARCHAR (200)  NOT NULL ,
                                  JOB_GROUP VARCHAR (200)  NOT NULL ,
                                  DESCRIPTION VARCHAR (250) NULL ,
                                  JOB_CLASS_NAME VARCHAR (250)  NOT NULL ,
                                  IS_DURABLE BOOLEAN  NOT NULL ,
                                  IS_NONCONCURRENT BOOLEAN  NOT NULL ,
                                  IS_UPDATE_DATA BOOLEAN  NOT NULL ,
                                  REQUESTS_RECOVERY BOOLEAN  NOT NULL ,
                                  JOB_DATA IMAGE NULL
);

CREATE TABLE IF NOT EXISTS QUARTZ_SIMPLE_TRIGGERS (
                                      SCHED_NAME VARCHAR(120) NOT NULL,
                                      TRIGGER_NAME VARCHAR (200)  NOT NULL ,
                                      TRIGGER_GROUP VARCHAR (200)  NOT NULL ,
                                      REPEAT_COUNT BIGINT NOT NULL ,
                                      REPEAT_INTERVAL BIGINT NOT NULL ,
                                      TIMES_TRIGGERED BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS QUARTZ_SIMPROP_TRIGGERS (
                                       SCHED_NAME VARCHAR(120) NOT NULL,
                                       TRIGGER_NAME VARCHAR(200) NOT NULL,
                                       TRIGGER_GROUP VARCHAR(200) NOT NULL,
                                       STR_PROP_1 VARCHAR(512) NULL,
                                       STR_PROP_2 VARCHAR(512) NULL,
                                       STR_PROP_3 VARCHAR(512) NULL,
                                       INT_PROP_1 INTEGER NULL,
                                       INT_PROP_2 INTEGER NULL,
                                       LONG_PROP_1 BIGINT NULL,
                                       LONG_PROP_2 BIGINT NULL,
                                       DEC_PROP_1 NUMERIC(13,4) NULL,
                                       DEC_PROP_2 NUMERIC(13,4) NULL,
                                       BOOL_PROP_1 BOOLEAN NULL,
                                       BOOL_PROP_2 BOOLEAN NULL
);

CREATE TABLE IF NOT EXISTS QUARTZ_BLOB_TRIGGERS (
                                    SCHED_NAME VARCHAR(120) NOT NULL,
                                    TRIGGER_NAME VARCHAR (200)  NOT NULL ,
                                    TRIGGER_GROUP VARCHAR (200)  NOT NULL ,
                                    BLOB_DATA IMAGE NULL
);

CREATE TABLE IF NOT EXISTS QUARTZ_TRIGGERS (
                               SCHED_NAME VARCHAR(120) NOT NULL,
                               TRIGGER_NAME VARCHAR (200)  NOT NULL ,
                               TRIGGER_GROUP VARCHAR (200)  NOT NULL ,
                               JOB_NAME VARCHAR (200)  NOT NULL ,
                               JOB_GROUP VARCHAR (200)  NOT NULL ,
                               DESCRIPTION VARCHAR (250) NULL ,
                               NEXT_FIRE_TIME BIGINT NULL ,
                               PREV_FIRE_TIME BIGINT NULL ,
                               PRIORITY INTEGER NULL ,
                               TRIGGER_STATE VARCHAR (16)  NOT NULL ,
                               TRIGGER_TYPE VARCHAR (8)  NOT NULL ,
                               START_TIME BIGINT NOT NULL ,
                               END_TIME BIGINT NULL ,
                               CALENDAR_NAME VARCHAR (200)  NULL ,
                               MISFIRE_INSTR SMALLINT NULL ,
                               JOB_DATA IMAGE NULL
);

ALTER TABLE QUARTZ_CALENDARS  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_CALENDARS PRIMARY KEY
        (
         SCHED_NAME,
         CALENDAR_NAME
            );

ALTER TABLE QUARTZ_CRON_TRIGGERS  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_CRON_TRIGGERS PRIMARY KEY
        (
         SCHED_NAME,
         TRIGGER_NAME,
         TRIGGER_GROUP
            );

ALTER TABLE QUARTZ_FIRED_TRIGGERS  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_FIRED_TRIGGERS PRIMARY KEY
        (
         SCHED_NAME,
         ENTRY_ID
            );

ALTER TABLE QUARTZ_PAUSED_TRIGGER_GRPS  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_PAUSED_TRIGGER_GRPS PRIMARY KEY
        (
         SCHED_NAME,
         TRIGGER_GROUP
            );

ALTER TABLE QUARTZ_SCHEDULER_STATE  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_SCHEDULER_STATE PRIMARY KEY
        (
         SCHED_NAME,
         INSTANCE_NAME
            );

ALTER TABLE QUARTZ_LOCKS  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_LOCKS PRIMARY KEY
        (
         SCHED_NAME,
         LOCK_NAME
            );

ALTER TABLE QUARTZ_JOB_DETAILS  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_JOB_DETAILS PRIMARY KEY
        (
         SCHED_NAME,
         JOB_NAME,
         JOB_GROUP
            );

ALTER TABLE QUARTZ_SIMPLE_TRIGGERS  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_SIMPLE_TRIGGERS PRIMARY KEY
        (
         SCHED_NAME,
         TRIGGER_NAME,
         TRIGGER_GROUP
            );

ALTER TABLE QUARTZ_SIMPROP_TRIGGERS  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_SIMPROP_TRIGGERS PRIMARY KEY
        (
         SCHED_NAME,
         TRIGGER_NAME,
         TRIGGER_GROUP
            );

ALTER TABLE QUARTZ_TRIGGERS  ADD
    CONSTRAINT IF NOT EXISTS PK_QUARTZ_TRIGGERS PRIMARY KEY
        (
         SCHED_NAME,
         TRIGGER_NAME,
         TRIGGER_GROUP
            );

ALTER TABLE QUARTZ_CRON_TRIGGERS ADD
    CONSTRAINT IF NOT EXISTS FK_QUARTZ_CRON_TRIGGERS_QUARTZ_TRIGGERS FOREIGN KEY
        (
         SCHED_NAME,
         TRIGGER_NAME,
         TRIGGER_GROUP
            ) REFERENCES QUARTZ_TRIGGERS (
                                        SCHED_NAME,
                                        TRIGGER_NAME,
                                        TRIGGER_GROUP
            ) ON DELETE CASCADE;


ALTER TABLE QUARTZ_SIMPLE_TRIGGERS ADD
    CONSTRAINT IF NOT EXISTS FK_QUARTZ_SIMPLE_TRIGGERS_QUARTZ_TRIGGERS FOREIGN KEY
        (
         SCHED_NAME,
         TRIGGER_NAME,
         TRIGGER_GROUP
            ) REFERENCES QUARTZ_TRIGGERS (
                                        SCHED_NAME,
                                        TRIGGER_NAME,
                                        TRIGGER_GROUP
            ) ON DELETE CASCADE;

ALTER TABLE QUARTZ_SIMPROP_TRIGGERS ADD
    CONSTRAINT IF NOT EXISTS FK_QUARTZ_SIMPROP_TRIGGERS_QUARTZ_TRIGGERS FOREIGN KEY
        (
         SCHED_NAME,
         TRIGGER_NAME,
         TRIGGER_GROUP
            ) REFERENCES QUARTZ_TRIGGERS (
                                        SCHED_NAME,
                                        TRIGGER_NAME,
                                        TRIGGER_GROUP
            ) ON DELETE CASCADE;


ALTER TABLE QUARTZ_TRIGGERS ADD
    CONSTRAINT IF NOT EXISTS FK_QUARTZ_TRIGGERS_QUARTZ_JOB_DETAILS FOREIGN KEY
        (
         SCHED_NAME,
         JOB_NAME,
         JOB_GROUP
            ) REFERENCES QUARTZ_JOB_DETAILS (
                                           SCHED_NAME,
                                           JOB_NAME,
                                           JOB_GROUP
            );

COMMIT;