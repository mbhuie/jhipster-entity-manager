import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConstituent, Constituent } from 'app/shared/model/constituent.model';
import { ConstituentService } from './constituent.service';
import { ConstituentComponent } from './constituent.component';
import { ConstituentDetailComponent } from './constituent-detail.component';
import { ConstituentUpdateComponent } from './constituent-update.component';

@Injectable({ providedIn: 'root' })
export class ConstituentResolve implements Resolve<IConstituent> {
  constructor(private service: ConstituentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConstituent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((constituent: HttpResponse<Constituent>) => {
          if (constituent.body) {
            return of(constituent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Constituent());
  }
}

export const constituentRoute: Routes = [
  {
    path: '',
    component: ConstituentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.constituent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConstituentDetailComponent,
    resolve: {
      constituent: ConstituentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.constituent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConstituentUpdateComponent,
    resolve: {
      constituent: ConstituentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.constituent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConstituentUpdateComponent,
    resolve: {
      constituent: ConstituentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.constituent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
