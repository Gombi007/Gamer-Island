export class GameDetails {


    private name: string="";
    private steam_appid: number=1;
    private required_age: string="";
    private short_description: string="";
    private pictures:{} = {};
/*
	constructor($name: string, $steam_appid: number, $required_age: string, $short_description: string, $pictures: Map<number, string> ) {
		this.name = $name;
		this.steam_appid = $steam_appid;
		this.required_age = $required_age;
		this.short_description = $short_description;
		this.pictures = $pictures;
	}
*/


    /**
     * Getter $name
     * @return {string}
     */
	public get $name(): string {
		return this.name;
	}

    /**
     * Getter $steam_appid
     * @return {number}
     */
	public get $steam_appid(): number {
		return this.steam_appid;
	}

    /**
     * Getter $required_age
     * @return {string}
     */
	public get $required_age(): string {
		return this.required_age;
	}

    /**
     * Getter $short_description
     * @return {string}
     */
	public get $short_description(): string {
		return this.short_description;
	}

    /**
     * Getter $pictures
     * @return {{} }
     */
	public get $pictures(): {}  {
		return this.pictures;
	}

    /**
     * Setter $name
     * @param {string} value
     */
	public set $name(value: string) {
		this.name = value;
	}

    /**
     * Setter $steam_appid
     * @param {number} value
     */
	public set $steam_appid(value: number) {
		this.steam_appid = value;
	}

    /**
     * Setter $required_age
     * @param {string} value
     */
	public set $required_age(value: string) {
		this.required_age = value;
	}

    /**
     * Setter $short_description
     * @param {string} value
     */
	public set $short_description(value: string) {
		this.short_description = value;
	}

    /**
     * Setter $pictures
     * @param {{} } value
     */
	public set $pictures(value: {} ) {
		this.pictures = value;
	}






}