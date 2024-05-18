import { CommunityModel } from "../community/community-model";
import { ImageResponsePackage } from "../shared/image-upload-service";

export class PostTemplateModel {
    id?: number;
    templateName?: string;
    ownerCommunity?: CommunityModel;
    dataFields?: DataField[];
}
export enum DataFields{
    TextField = "TextField",
    ImageField = "ImageField",
    DateField = "DateField",
    UrlField = "UrlField"
}
export class DataField{
    id?: number;
    name?: string;
    type?: string;
}

export class TextField extends DataField{
    input?: string;
    constructor(){
        super();
        this.type = this.constructor.name;
    }
}
export class UrlField extends DataField{
    input?: string;
    constructor(){
        super();
        this.type = this.constructor.name;
    }
}
export class DateSField extends DataField{
    input?: string;
    constructor(){
        super();
        this.type = this.constructor.name;
    }
}
export class ImageField extends DataField{
    file?: File;
    //image id to be set in backend
    input?: number;
    
    constructor(){
        super();
        this.type = this.constructor.name;
    }
}

