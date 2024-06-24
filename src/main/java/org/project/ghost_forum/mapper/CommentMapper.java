package org.project.ghost_forum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.project.ghost_forum.dto.CommentDto;
import org.project.ghost_forum.entity.Comment;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
 nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
 unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
 @Mapping(target = "postId", source = "post.id")
 @Mapping(target = "userId", source = "user.id")
 @Mapping(target = "userUsername", source = "user.username")
 CommentDto toDto (Comment comment);

 @Mapping(source = "postId", target = "post.id")
 @Mapping(source = "userId", target = "user.id")
 Comment toEntity (CommentDto comment);
}