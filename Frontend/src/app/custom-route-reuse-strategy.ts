import { ActivatedRouteSnapshot, BaseRouteReuseStrategy, DetachedRouteHandle } from "@angular/router";

export class CustomRouteReuseStrategy extends BaseRouteReuseStrategy {
    private storedRoutes = new Map<string, DetachedRouteHandle>();

    override shouldDetach(route: ActivatedRouteSnapshot): boolean {
        console.log(route.routeConfig!.path )      
        return route.routeConfig!.path === 'store';
        
    }
    
    override store(route: ActivatedRouteSnapshot, detachedTree: DetachedRouteHandle): void {        
        this.storedRoutes.set(route.routeConfig!.path!, detachedTree);
        
    };
    
    
    override shouldAttach(route: ActivatedRouteSnapshot): boolean {    
        return !!this.storedRoutes.get(route.routeConfig!.path!);

    }

    override retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle | null {
       
        return this.storedRoutes.get(route.routeConfig!.path!) as DetachedRouteHandle;
    }

}