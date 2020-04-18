import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExchange, Exchange } from 'app/shared/model/exchange.model';
import { ExchangeService } from './exchange.service';
import { ExchangeComponent } from './exchange.component';
import { ExchangeDetailComponent } from './exchange-detail.component';
import { ExchangeUpdateComponent } from './exchange-update.component';

@Injectable({ providedIn: 'root' })
export class ExchangeResolve implements Resolve<IExchange> {
  constructor(private service: ExchangeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExchange> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((exchange: HttpResponse<Exchange>) => {
          if (exchange.body) {
            return of(exchange.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Exchange());
  }
}

export const exchangeRoute: Routes = [
  {
    path: '',
    component: ExchangeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.exchange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExchangeDetailComponent,
    resolve: {
      exchange: ExchangeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.exchange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExchangeUpdateComponent,
    resolve: {
      exchange: ExchangeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.exchange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExchangeUpdateComponent,
    resolve: {
      exchange: ExchangeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'entityManagerApp.exchange.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
