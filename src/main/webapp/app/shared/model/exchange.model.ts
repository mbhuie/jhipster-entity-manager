import { IStock } from 'app/shared/model/stock.model';

export interface IExchange {
  id?: number;
  mic?: string;
  name?: string;
  mic?: IStock;
}

export class Exchange implements IExchange {
  constructor(public id?: number, public mic?: string, public name?: string, public mic?: IStock) {}
}
