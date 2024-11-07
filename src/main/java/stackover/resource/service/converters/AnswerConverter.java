package stackover.resource.service.converters;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import stackover.resource.service.dto.request.AnswerRequestDto;
import stackover.resource.service.entity.question.answer.Answer;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnswerConverter {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "question.id", source = "questionId")
    @Mapping(target = "htmlBody", source = "body")
    @Mapping(target = "persistDateTime", source = "persistDate")
    @Mapping(target = "dateAcceptTime", source = "dateAcceptTime")
    Answer answerRequestDtoToAnswer(AnswerRequestDto answerRequestDto);

    @AfterMapping
    default void afterToAnswer(@MappingTarget Answer answer) {
        answer.setIsDeleted(false);
        answer.setIsDeletedByModerator(false);
    }
}
