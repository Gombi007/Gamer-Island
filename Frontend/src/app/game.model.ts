export class Game {
    id: number;
    appid: number;
    name: string;
    headerImg: string;

    constructor(id: number, appid:number, name: string, headerImg: string) {
        this.id = id;
        this.appid = appid;
        this.name = name;
        this.headerImg = headerImg;
    }
}