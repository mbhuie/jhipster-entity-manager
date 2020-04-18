import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EntityManagerSharedModule } from 'app/shared/shared.module';
import { ConstituentComponent } from './constituent.component';
import { ConstituentDetailComponent } from './constituent-detail.component';
import { ConstituentUpdateComponent } from './constituent-update.component';
import { ConstituentDeleteDialogComponent } from './constituent-delete-dialog.component';
import { constituentRoute } from './constituent.route';

@NgModule({
  imports: [EntityManagerSharedModule, RouterModule.forChild(constituentRoute)],
  declarations: [ConstituentComponent, ConstituentDetailComponent, ConstituentUpdateComponent, ConstituentDeleteDialogComponent],
  entryComponents: [ConstituentDeleteDialogComponent]
})
export class EntityManagerConstituentModule {}
