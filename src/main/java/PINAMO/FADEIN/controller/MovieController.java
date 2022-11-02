package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.Movie;
import PINAMO.FADEIN.data.dto.movie.mainPageDTO;
import PINAMO.FADEIN.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/contents")
public class MovieController {

  private MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping(value = "/")
  public mainPageDTO getMainPage(@RequestParam String type) {
    List<Movie> popular = movieService.getPopular(type);
    List<Movie> topRate = movieService.getTopRate(type);
    List<Movie> nowPlaying = movieService.getNowPlaying(type);
    List<Movie> preference = movieService.getPopular(type);
    List<Movie> recommend = movieService.getPopular(type);

    return movieService.getMainPage(popular, topRate, nowPlaying, preference, recommend);
  }

}
