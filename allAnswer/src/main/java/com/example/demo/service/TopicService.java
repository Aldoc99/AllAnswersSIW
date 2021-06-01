package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Domanda;
import com.example.demo.model.Topic;
import com.example.demo.repository.TopicRepository;

@Service
public class TopicService {
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Transactional
	public Topic inserisci(Topic topic) {
		return topicRepository.save(topic);
	}
	
	@Transactional
	public Topic getById(Long id) {
		return topicRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Topic> tutti(){
		return (List<Topic>) topicRepository.findAll();
	}

	
}
