package com.board.post.controller;

import com.board.attachfile.service.AttachFileService;
import com.board.post.dto.PostDtoRequest;
import com.board.post.dto.PostDtoResponse;
import com.board.post.entity.Post;
import com.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostMvcController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String create() {
        return "postForm";
    }

    @GetMapping("/{postId}")
    public String read(@PathVariable Long postId, Model model) {
        Post post = postService.read(postId);
        PostDtoResponse response = modelMapper.map(post, PostDtoResponse.class);
        model.addAttribute(response);
        return "postDetail";
    }



    @PostMapping
    public String itemNew(@Valid PostDtoRequest postDtoRequest,
                          @RequestParam("attachFiles") List<MultipartFile> attachFiles) {
        postService.saveWithAttach(postDtoRequest,attachFiles);

        return "redirect:/";
    }
}
