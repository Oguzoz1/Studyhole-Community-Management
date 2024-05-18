import { DataField, PostTemplateModel } from "../post-template-model";

export class CreatePostPayload {
    postTitle?: string;
    communityId?: number;
    description?: string;
    postTemplate?: PostTemplateModel;
    postTemplateId?: number;
    content?: DataField[];
}