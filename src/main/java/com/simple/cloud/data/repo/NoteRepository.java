package com.simple.cloud.data.repo;

import com.simple.cloud.data.model.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long> {
}
