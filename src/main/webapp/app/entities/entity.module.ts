import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'index',
        loadChildren: () => import('./index/index.module').then(m => m.EntityManagerIndexModule)
      },
      {
        path: 'constituent',
        loadChildren: () => import('./constituent/constituent.module').then(m => m.EntityManagerConstituentModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EntityManagerEntityModule {}
