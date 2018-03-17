import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PlatComponent } from './plat.component';
import { PlatDetailComponent } from './plat-detail.component';
import { PlatPopupComponent } from './plat-dialog.component';
import { PlatDeletePopupComponent } from './plat-delete-dialog.component';

export const platRoute: Routes = [
    {
        path: 'plat',
        component: PlatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'menuwebApp.plat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'plat/:id',
        component: PlatDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'menuwebApp.plat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const platPopupRoute: Routes = [
    {
        path: 'plat-new',
        component: PlatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'menuwebApp.plat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plat/:id/edit',
        component: PlatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'menuwebApp.plat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plat/:id/delete',
        component: PlatDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'menuwebApp.plat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
