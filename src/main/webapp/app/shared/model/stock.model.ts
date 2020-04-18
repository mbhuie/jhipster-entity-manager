export interface IStock {
  id?: number;
  ric?: string;
  name?: string;
  mic?: string;
  lotSize?: number;
}

export class Stock implements IStock {
  constructor(public id?: number, public ric?: string, public name?: string, public mic?: string, public lotSize?: number) {}
}
