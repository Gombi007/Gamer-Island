export class Steam {
    appid: number;
    img: string ="https://cdn.cloudflare.steamstatic.com/steam/apps/";
    name: string;

    constructor(appid: number, img: string, name: string) {
        this.appid = appid;
        this.img = this.img + img;
        this.name = name;
    }
}