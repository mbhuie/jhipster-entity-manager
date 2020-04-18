import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'index-etf',
        loadChildren: () => import('./index-etf/index-etf.module').then(m => m.EntityManagerIndexETFModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EntityManagerEntityModule {}
