package com.simple.cloud.service;

import com.simple.cloud.data.model.Note;
import com.simple.cloud.data.repo.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Iterable<Note> getAllNotes() {
        return noteRepository.findAll();
    }

}
