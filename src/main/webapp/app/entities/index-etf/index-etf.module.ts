import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EntityManagerSharedModule } from 'app/shared/shared.module';
import { IndexETFComponent } from './index-etf.component';
import { IndexETFDetailComponent } from './index-etf-detail.component';
import { IndexETFUpdateComponent } from './index-etf-update.component';
import { IndexETFDeleteDialogComponent } from './index-etf-delete-dialog.component';
import { indexETFRoute } from './index-etf.route';

@NgModule({
  imports: [EntityManagerSharedModule, RouterModule.forChild(indexETFRoute)],
  declarations: [IndexETFComponent, IndexETFDetailComponent, IndexETFUpdateComponent, IndexETFDeleteDialogComponent],
  entryComponents: [IndexETFDeleteDialogComponent]
})
export class EntityManagerIndexETFModule {}
