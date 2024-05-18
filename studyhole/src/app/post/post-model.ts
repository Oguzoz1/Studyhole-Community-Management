import { DataField, PostTemplateModel } from "./post-template-model";

export class PostModel {
    postId?: number;
    postTitle?: string;
    url?: string;
    postTemplate?: PostTemplateModel;
    postTemplateId?: number;
    description?: string;
    voteCount?: number;
    username?: string;
    communityName?: string;
    commentCount?: number;
    upVote?: boolean;
    downVote?: boolean;
    content?: DataField[];
}
