import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TipoAtributoSpComponent } from './tipo-atributo-sp.component';
import { TipoAtributoSpDetailComponent } from './tipo-atributo-sp-detail.component';
import { TipoAtributoSpPopupComponent } from './tipo-atributo-sp-dialog.component';
import { TipoAtributoSpDeletePopupComponent } from './tipo-atributo-sp-delete-dialog.component';

export const tipoAtributoRoute: Routes = [
    {
        path: 'tipo-atributo-sp',
        component: TipoAtributoSpComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoAtributos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tipo-atributo-sp/:id',
        component: TipoAtributoSpDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoAtributos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoAtributoPopupRoute: Routes = [
    {
        path: 'tipo-atributo-sp-new',
        component: TipoAtributoSpPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoAtributos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-atributo-sp/:id/edit',
        component: TipoAtributoSpPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoAtributos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-atributo-sp/:id/delete',
        component: TipoAtributoSpDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoAtributos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
