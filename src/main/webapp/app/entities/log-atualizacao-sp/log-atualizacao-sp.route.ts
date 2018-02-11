import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LogAtualizacaoSpComponent } from './log-atualizacao-sp.component';
import { LogAtualizacaoSpDetailComponent } from './log-atualizacao-sp-detail.component';
import { LogAtualizacaoSpPopupComponent } from './log-atualizacao-sp-dialog.component';
import { LogAtualizacaoSpDeletePopupComponent } from './log-atualizacao-sp-delete-dialog.component';

export const logAtualizacaoRoute: Routes = [
    {
        path: 'log-atualizacao-sp',
        component: LogAtualizacaoSpComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogAtualizacaos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'log-atualizacao-sp/:id',
        component: LogAtualizacaoSpDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogAtualizacaos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logAtualizacaoPopupRoute: Routes = [
    {
        path: 'log-atualizacao-sp-new',
        component: LogAtualizacaoSpPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogAtualizacaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-atualizacao-sp/:id/edit',
        component: LogAtualizacaoSpPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogAtualizacaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-atualizacao-sp/:id/delete',
        component: LogAtualizacaoSpDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogAtualizacaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
