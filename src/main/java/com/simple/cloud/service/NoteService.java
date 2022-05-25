package com.simple.cloud.service;

import com.simple.cloud.data.model.Note;
import com.simple.cloud.data.repo.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Iterable<Note> getAllNotes() {
        return noteRepository.findAll();
    }

}
