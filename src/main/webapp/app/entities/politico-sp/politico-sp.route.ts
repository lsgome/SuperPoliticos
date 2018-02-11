import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PoliticoSpComponent } from './politico-sp.component';
import { PoliticoSpDetailComponent } from './politico-sp-detail.component';
import { PoliticoSpPopupComponent } from './politico-sp-dialog.component';
import { PoliticoSpDeletePopupComponent } from './politico-sp-delete-dialog.component';

@Injectable()
export class PoliticoSpResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const politicoRoute: Routes = [
    {
        path: 'politico-sp',
        component: PoliticoSpComponent,
        resolve: {
            'pagingParams': PoliticoSpResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Politicos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'politico-sp/:id',
        component: PoliticoSpDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Politicos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const politicoPopupRoute: Routes = [
    {
        path: 'politico-sp-new',
        component: PoliticoSpPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Politicos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'politico-sp/:id/edit',
        component: PoliticoSpPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Politicos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'politico-sp/:id/delete',
        component: PoliticoSpDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Politicos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
