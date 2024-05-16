import { CommunityModel } from "../community/community-model";

export class PostTemplateModel {
    id?: number;
    templateName?: string;
    ownerCommunity?: CommunityModel;
    dataFields?: DataField[];
}
export enum DataFields{
    Text = "Text",
    Image = "Image",
    Date = "Date",
    URL= "URL"
}
export class DataField{
    id?: number;
    name?: string;
}

export class TextField extends DataField{
    input?: string;
    type?: string;
    constructor(){
        super();
        this.type = this.constructor.name;
    }
}
export class UrlField extends DataField{
    input?: string;
    type?: string;
    constructor(){
        super();
        this.type = this.constructor.name;
    }
}
export class DateField extends DataField{
    input?: Date;
    type?: string;
    constructor(){
        super();
        this.type = this.constructor.name;
    }
}
export class ImageField extends DataField{
    input?: File;
    type?: string;
    constructor(){
        super();
        this.type = this.constructor.name;
    }
}

