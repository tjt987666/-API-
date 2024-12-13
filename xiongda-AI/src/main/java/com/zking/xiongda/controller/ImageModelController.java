//package com.zking.xiongda.controller;
//
//import org.springframework.ai.image.*;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ImageModelController {
//  private final ImageModel imageModel;
//
//  ImageModelController(ImageModel imageModel) {
//    this.imageModel = imageModel;
//  }
//
//  @RequestMapping("/image")
//  public String image(String input) {
//    ImageOptions options = ImageOptionsBuilder.builder()
//        .withModel("dall-e-3")
//        .build();
//
//    ImagePrompt imagePrompt = new ImagePrompt(input, options);
//    ImageResponse response = imageModel.call(imagePrompt);
//    String imageUrl = response.getResult().getOutput().getUrl();
//
//    System.out.println(imageUrl);
//    return "redirect:" + imageUrl;
//  }
//}