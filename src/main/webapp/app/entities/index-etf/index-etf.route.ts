import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIndexETF, IndexETF } from 'app/shared/model/index-etf.model';
import { IndexETFService } from './index-etf.service';
import { IndexETFComponent } from './index-etf.component';
import { IndexETFDetailComponent } from './index-etf-detail.component';
import { IndexETFUpdateComponent } from './index-etf-update.component';

@Injectable({ providedIn: 'root' })
export class IndexETFResolve implements Resolve<IIndexETF> {
  constructor(private service: IndexETFService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndexETF> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((indexETF: HttpResponse<IndexETF>) => {
          if (indexETF.body) {
            return of(indexETF.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IndexETF());
  }
}

export const indexETFRoute: Routes = [
  {
    path: '',
    component: IndexETFComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.indexETF.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IndexETFDetailComponent,
    resolve: {
      indexETF: IndexETFResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.indexETF.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IndexETFUpdateComponent,
    resolve: {
      indexETF: IndexETFResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.indexETF.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IndexETFUpdateComponent,
    resolve: {
      indexETF: IndexETFResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.indexETF.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
