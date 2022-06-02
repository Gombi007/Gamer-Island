export class Game {
    id: number;
    appId: number;
    name: string;
    headerImage: string;

	constructor($id: number, $appId: number, $name: string, $headerImage: string) {
		this.id = $id;
		this.appId = $appId;
		this.name = $name;
		this.headerImage = $headerImage;
	}


    /**
     * Getter $id
     * @return {number}
     */
	public get $id(): number {
		return this.id;
	}

    /**
     * Getter $appId
     * @return {number}
     */
	public get $appId(): number {
		return this.appId;
	}

    /**
     * Getter $name
     * @return {string}
     */
	public get $name(): string {
		return this.name;
	}

    /**
     * Getter $headerImage
     * @return {string}
     */
	public get $headerImage(): string {
		return this.headerImage;
	}

    /**
     * Setter $id
     * @param {number} value
     */
	public set $id(value: number) {
		this.id = value;
	}

    /**
     * Setter $appId
     * @param {number} value
     */
	public set $appId(value: number) {
		this.appId = value;
	}

    /**
     * Setter $name
     * @param {string} value
     */
	public set $name(value: string) {
		this.name = value;
	}

    /**
     * Setter $headerImage
     * @param {string} value
     */
	public set $headerImage(value: string) {
		this.headerImage = value;
	}

   

}