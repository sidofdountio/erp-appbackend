package com.sidof.app.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

// Hibernate PersistentSet Mixin (The Fix for your specific error)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)


public abstract class HibernatePersistentSetMixin {}

