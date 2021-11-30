package com.pravdin.democassandra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("internship_manager")
public class InternshipManager extends User{
}
