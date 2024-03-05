package com.kodnest.tunehub.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.service.SongService;
@Controller
public class SongController {
	//To Add a Song in DB
	@Autowired
	SongService songService;

	@PostMapping("/addsong")
	public String addSong(@ModelAttribute Song song) {

		//Song taken from the Admin
		//checking if the entered song is present in DB or not
		boolean status = songService.songExists(song.getName());

		if(status == false) {
			songService.addSong(song);
			System.out.println("Song Added");
		}
		else {
			System.out.println("Song Already Exists");
		}

		return "adminhome";
	}
	
	//To View All the Songs
	@GetMapping("/viewsongs")
	public String viewSongs(Model model){
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "displaysongs";
	}
	
	@GetMapping("/playsongs")
	public String playSongs(Model model){
		boolean premium = true;
		if(premium) {
			List<Song> songList = songService.fetchAllSongs();
			model.addAttribute("songs", songList);
			return "displaysongs";
		}
		else {
			return "subscriptionform";
		}
	}
}
