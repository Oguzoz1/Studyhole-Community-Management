import { ImageModel } from "../shared/image-upload-service";
import { UserModel } from "../user/user-model";

export class CommunityModel{
    communityId? : number;
    name?: string;
    description?: string;
    createdDate?: Date;
    numberOfPosts?: number;
    publicCommunity?: boolean;
    ownerUsers?: UserModel[];
    memberIds?: number[];
    image?: ImageModel;
}