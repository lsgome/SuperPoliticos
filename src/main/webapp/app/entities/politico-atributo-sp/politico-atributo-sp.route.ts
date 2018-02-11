import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PoliticoAtributoSpComponent } from './politico-atributo-sp.component';
import { PoliticoAtributoSpDetailComponent } from './politico-atributo-sp-detail.component';
import { PoliticoAtributoSpPopupComponent } from './politico-atributo-sp-dialog.component';
import { PoliticoAtributoSpDeletePopupComponent } from './politico-atributo-sp-delete-dialog.component';

export const politicoAtributoRoute: Routes = [
    {
        path: 'politico-atributo-sp',
        component: PoliticoAtributoSpComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PoliticoAtributos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'politico-atributo-sp/:id',
        component: PoliticoAtributoSpDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PoliticoAtributos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const politicoAtributoPopupRoute: Routes = [
    {
        path: 'politico-atributo-sp-new',
        component: PoliticoAtributoSpPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PoliticoAtributos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'politico-atributo-sp/:id/edit',
        component: PoliticoAtributoSpPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PoliticoAtributos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'politico-atributo-sp/:id/delete',
        component: PoliticoAtributoSpDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PoliticoAtributos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
