import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'stock',
        loadChildren: () => import('./stock/stock.module').then(m => m.EntityManagerStockModule)
      },
      {
        path: 'exchange',
        loadChildren: () => import('./exchange/exchange.module').then(m => m.EntityManagerExchangeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EntityManagerEntityModule {}
