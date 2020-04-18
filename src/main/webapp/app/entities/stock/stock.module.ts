import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EntityManagerSharedModule } from 'app/shared/shared.module';
import { StockComponent } from './stock.component';
import { StockDetailComponent } from './stock-detail.component';
import { StockUpdateComponent } from './stock-update.component';
import { StockDeleteDialogComponent } from './stock-delete-dialog.component';
import { stockRoute } from './stock.route';

@NgModule({
  imports: [EntityManagerSharedModule, RouterModule.forChild(stockRoute)],
  declarations: [StockComponent, StockDetailComponent, StockUpdateComponent, StockDeleteDialogComponent],
  entryComponents: [StockDeleteDialogComponent]
})
export class EntityManagerStockModule {}
