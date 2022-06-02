
export class GameDetails {

    id: number = 999999999;
    steam_appid: number = 999999999;
    success: boolean = false;
    name: string = "TEST NAME";
    required_age: string = "0";
    is_free: boolean = false;
    detailed_description: string = "TEST DETAILED DESC";
    about_the_game: string = "TEST ABOUT";
    short_description: string = "TEST SHORT DESC";
    supported_languages: string = "TEST LANGUAGES";
    header_image: string = "TEST HEADER IMG";
    website: string = "TEST WEBSITE";
    developers: string = "TEST DEVELOPERS";
    publishers: string = "TEST PUBLISHERS";
    price_in_final_formatted: string = "TEST PRICE €";
    platforms: GamePlatform = { linux: false, windows: false, mac: false };
    metacritics: Map<String, String> = new Map<String, String>();
    screenshot_urls: string[] = [];
    genres: string[] = [];


    /**
     * Getter $id
     * @return {number }
     */
    public get $id(): number {
        return this.id;
    }

    /**
     * Getter $steam_appid
     * @return {number }
     */
    public get $steam_appid(): number {
        return this.steam_appid;
    }

    /**
     * Getter $success
     * @return {boolean }
     */
    public get $success(): boolean {
        return this.success;
    }

    /**
     * Getter $name
     * @return {string }
     */
    public get $name(): string {
        return this.name;
    }

    /**
     * Getter $required_age
     * @return {string }
     */
    public get $required_age(): string {
        return this.required_age;
    }

    /**
     * Getter $is_free
     * @return {boolean }
     */
    public get $is_free(): boolean {
        return this.is_free;
    }

    /**
     * Getter $detailed_description
     * @return {string }
     */
    public get $detailed_description(): string {
        return this.detailed_description;
    }

    /**
     * Getter $about_the_game
     * @return {string }
     */
    public get $about_the_game(): string {
        return this.about_the_game;
    }

    /**
     * Getter $short_description
     * @return {string }
     */
    public get $short_description(): string {
        return this.short_description;
    }

    /**
     * Getter $supported_languages
     * @return {string }
     */
    public get $supported_languages(): string {
        return this.supported_languages;
    }

    /**
     * Getter $header_image
     * @return {string }
     */
    public get $header_image(): string {
        return this.header_image;
    }

    /**
     * Getter $website
     * @return {string }
     */
    public get $website(): string {
        return this.website;
    }

    /**
     * Getter $developers
     * @return {string }
     */
    public get $developers(): string {
        return this.developers;
    }

    /**
     * Getter $publishers
     * @return {string }
     */
    public get $publishers(): string {
        return this.publishers;
    }

    /**
     * Getter $price_in_final_formatted
     * @return {string }
     */
    public get $price_in_final_formatted(): string {
        return this.price_in_final_formatted;
    }

    /**
     * Getter $platforms
     * @return {GamePlatform }
     */
    public get $platforms(): GamePlatform {
        return this.platforms;
    }

    /**
     * Getter $metacritics
     * @return {Map<String, String> }
     */
    public get $metacritics(): Map<String, String> {
        return this.metacritics;
    }

    /**
     * Getter $screenshot_urls
     * @return {string[] }
     */
    public get $screenshot_urls(): string[] {
        return this.screenshot_urls;
    }

    /**
     * Getter $genres
     * @return {string[] }
     */
    public get $genres(): string[] {
        return this.genres;
    }

    /**
     * Setter $id
     * @param {number } value
     */
    public set $id(value: number) {
        this.id = value;
    }

    /**
     * Setter $steam_appid
     * @param {number } value
     */
    public set $steam_appid(value: number) {
        this.steam_appid = value;
    }

    /**
     * Setter $success
     * @param {boolean } value
     */
    public set $success(value: boolean) {
        this.success = value;
    }

    /**
     * Setter $name
     * @param {string } value
     */
    public set $name(value: string) {
        this.name = value;
    }

    /**
     * Setter $required_age
     * @param {string } value
     */
    public set $required_age(value: string) {
        this.required_age = value;
    }

    /**
     * Setter $is_free
     * @param {boolean } value
     */
    public set $is_free(value: boolean) {
        this.is_free = value;
    }

    /**
     * Setter $detailed_description
     * @param {string } value
     */
    public set $detailed_description(value: string) {
        this.detailed_description = value;
    }

    /**
     * Setter $about_the_game
     * @param {string } value
     */
    public set $about_the_game(value: string) {
        this.about_the_game = value;
    }

    /**
     * Setter $short_description
     * @param {string } value
     */
    public set $short_description(value: string) {
        this.short_description = value;
    }

    /**
     * Setter $supported_languages
     * @param {string } value
     */
    public set $supported_languages(value: string) {
        this.supported_languages = value;
    }

    /**
     * Setter $header_image
     * @param {string } value
     */
    public set $header_image(value: string) {
        this.header_image = value;
    }

    /**
     * Setter $website
     * @param {string } value
     */
    public set $website(value: string) {
        this.website = value;
    }

    /**
     * Setter $developers
     * @param {string } value
     */
    public set $developers(value: string) {
        this.developers = value;
    }

    /**
     * Setter $publishers
     * @param {string } value
     */
    public set $publishers(value: string) {
        this.publishers = value;
    }

    /**
     * Setter $price_in_final_formatted
     * @param {string } value
     */
    public set $price_in_final_formatted(value: string) {
        this.price_in_final_formatted = value;
    }

    /**
     * Setter $platforms
     * @param {GamePlatform } value
     */
    public set $platforms(value: GamePlatform) {
        this.platforms = value;
    }

    /**
     * Setter $metacritics
     * @param {Map<String, String> } value
     */
    public set $metacritics(value: Map<String, String>) {
        this.metacritics = value;
    }

    /**
     * Setter $screenshot_urls
     * @param {string[] } value
     */
    public set $screenshot_urls(value: string[]) {
        this.screenshot_urls = value;
    }

    /**
     * Setter $genres
     * @param {string[] } value
     */
    public set $genres(value: string[]) {
        this.genres = value;
    }



}

export interface GamePlatform {
    linux: boolean,
    windows: boolean,
    mac: boolean
}



