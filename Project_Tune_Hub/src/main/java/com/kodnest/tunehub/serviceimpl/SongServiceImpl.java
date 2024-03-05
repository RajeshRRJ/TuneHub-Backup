package com.kodnest.tunehub.serviceimpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.repository.SongRepository;
import com.kodnest.tunehub.service.SongService;
@Service
public class SongServiceImpl implements SongService{
	@Autowired
	SongRepository songRepository;
	//To Add a Song
	@Override
	public void addSong(Song song) {
		songRepository.save(song);
	}
	
	//To Add a Unique Song by Name
	@Override
	public boolean songExists(String name) {
		Song songStatus = songRepository.findByName(name);
		if(songStatus == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	//To View a List of Songs
	@Override
	public List<Song> fetchAllSongs() {
		List<Song> songs = songRepository.findAll();
		return songs;
	}

	@Override
	public void updateSong(Song s) {
		songRepository.save(s);
	}
}
