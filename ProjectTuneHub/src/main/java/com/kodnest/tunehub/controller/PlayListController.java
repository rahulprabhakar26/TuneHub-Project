package com.kodnest.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kodnest.tunehub.entity.PlayList;
import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.service.PlayListService;
import com.kodnest.tunehub.service.SongService;

@Controller
public class PlayListController {
	
	@Autowired
	SongService songService;
	
	@Autowired
	PlayListService playlistService;
	
	@GetMapping("/createplaylists")
	public String creratePlaylist(Model model) {
		List<Song> songList=songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createplaylists";	
	}
	
	@PostMapping("/addplaylist")
	public String addplaylist(@ModelAttribute PlayList playlist) {
		
		//updating the playlist table
		playlistService.addplaylist(playlist);
		
		//updating the song table
		List<Song> songList = playlist.getSongs();
		for (Song s : songList) {
			s.getPlaylists().add(playlist);
			songService.updateSong(s);
		}
		return "AdminHome";
	}
	
	
	
	@GetMapping("/viewplaylists")
	public String viewplaylist(Model model) {
		List<PlayList> songPlayList=playlistService.fetchAllPlayList();
		model.addAttribute("playlist", songPlayList);
	
		return "displayPlaylist";
		
	}
	
	@GetMapping("/updateplaylist")
	public String updatePlaylist(Model model) {
	List<PlayList> playlist=playlistService.fetchAllPlayLists(); 
		model.addAttribute("playList", playlist);
		return "UpdatePlaylist";	
	}
	
	@PutMapping("/playlist/{id}")
	public String updateplayList(@PathVariable Integer id, @RequestBody String name) {
		playlistService.updateplayList(id, name);
	
		
		return "UpdatePlaylist";
	}
	
//	 public String updatePlaylistName(@PathVariable Integer id, @RequestParam String newName) {
//        playlistService.updatePlaylistName(id, newName);
//        return "UpdatePlaylist";
//    }
}
