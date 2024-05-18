package com.studyhole.app.service;

import java.util.List;
import java.util.zip.DataFormatException;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.studyhole.app.data.ImagePackage;
import com.studyhole.app.data.PostPackage;
import com.studyhole.app.data.PostResponsePackage;
import com.studyhole.app.data.PostTemplatePackage;
import com.studyhole.app.mapper.ImageMapper;
import com.studyhole.app.mapper.PostMapper;
import com.studyhole.app.mapper.PostTemplateMapper;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.DataTypes.DataField;
import com.studyhole.app.model.DataTypes.DateSField;
import com.studyhole.app.model.DataTypes.Image;
import com.studyhole.app.model.DataTypes.ImageField;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.model.Post.PostTemplate;
import com.studyhole.app.repository.DataFieldRepository;
import com.studyhole.app.repository.ImageRepository;
import com.studyhole.app.repository.PostRepository;
import com.studyhole.app.repository.PostTemplateRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

import java.io.IOException;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final PostTemplateRepository templateRepository;
    private final PostTemplateMapper templateMapper;
    //Services
    private final StudyholeService studyholeService;
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final DataFieldRepository dataFieldRepository;

    @Transactional
    public PostResponsePackage save(PostPackage postPackage, Long id) throws IOException {
        Community com = studyholeService.getCommunityById(id);
        User currentUser = studyholeService.getCurrentUser();
        Post post =  postMapper.map(postPackage, com, currentUser);
        var savePost = postRepository.save(post);
        for (DataField data : savePost.getContent()) {
            data.setPostId(post.getPostId());
        }  
        return postMapper.mapToDto(savePost);
    }
    @Transactional
    public PostTemplatePackage saveTemplate(PostTemplatePackage templatePackage,Long id) {
        Community com = studyholeService.getCommunityById(id);
        templatePackage.setOwnerCommunity(com);
        var save = templateRepository.save(templateMapper.mapToData(templatePackage));
    
        return templateMapper.mapToPackage(save);
    }
    @Transactional
    public PostResponsePackage getPostResponsePackagebyId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException(id.toString() + "NOT FOUND"));
        return postMapper.mapToDto(post);
    }
    @Transactional
    public List<PostResponsePackage> getPostsByCommunity(Long id) {
        Community com = studyholeService.getCommunityById(id);
        
        var posts = postRepository.findAllByOwnerCommunity(com).stream()
        .map(postMapper::mapToDto).collect(toList());
        return posts;
    }

    @Transactional
    public List<PostResponsePackage> getPostResponsesPakcagesByUsername(String username) {
        User user = studyholeService.getUserbyUsername(username);
        var posts = postRepository.findByUser(user).stream().map(postMapper::mapToDto)
        .collect(toList());
        return posts;
    }
    @Transactional
    public List<PostResponsePackage> getAllPosts(String username) {
        var posts = postRepository.findAll().stream().map(postMapper::mapToDto)
        .collect(toList());
        return posts;
    }

    //Intended for method-use
    @Transactional
    public Post getPostById(Long Id){
        Post post = postRepository.findById(Id)
        .orElseThrow(() -> new RuntimeException("Post with given ID not found"));
        return post;
    }
    @Transactional
    public PostTemplatePackage getPostTemplatebyId(Long id) {
        PostTemplate template = templateRepository.findById(id).orElseThrow(() -> new RuntimeException(id.toString() + "NOT FOUND"));

        return templateMapper.mapToPackage(template);
    }
    @Transactional
    public List<PostTemplatePackage> getAllPostTemplateByCommunityId(Long id) {
        Community com = studyholeService.getCommunityById(id);
        List<PostTemplate> template = templateRepository.findAllByOwnerCommunity(com);

        return template.stream().map(templateMapper::mapToPackage).collect(toList());
    }
    @Transactional
    public List<DataField> getContentbyPostId(Long id) {
        Post post = getPostById(id);
        return post.getContent();
    }
    @Transactional
    public String uploadImageDataFieldbyDatafieldId(Long id, MultipartFile file) throws IOException {
        // Retrieve the data field by ID
        DataField data = getDataFieldbyId(id);
    
        // Ensure the data field is an instance of ImageField
        if (!(data instanceof ImageField)) {
            throw new IllegalArgumentException("DataField is not an instance of ImageField");
        }
    
        ImageField imageField = (ImageField) data;
    
        // Compress and save the image
        byte[] imageData = ImageUtils.compressImage(file.getBytes());
        Image image = Image.builder().imageData(imageData).build();
        Image im = imageRepository.save(image);
    
        // Set the image ID if it's not already set
        if (imageField.getInput() == null) {
            imageField.setInput(im.getId());
        }
    
        return "uploaded";
    }
    @Transactional
    public ImagePackage getDataFieldImagebyImageId(Long id) throws IOException, DataFormatException{
        Image im = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("ID NOT FOUND FOR IMAGE"));
        ImagePackage imPack =  imageMapper.mapToPackage(im);
        imPack.setImageData(ImageUtils.decompressImage(imPack.getImageData()));

        return imPack;
    }

    @Transactional
    public DataField getDataFieldbyId(Long id){
        DataField field = dataFieldRepository.findById(id).orElseThrow(() -> new RuntimeException("no id"));
        return field;
    }
}
