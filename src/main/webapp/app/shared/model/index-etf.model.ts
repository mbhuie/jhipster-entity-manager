import { Moment } from 'moment';

export interface IIndexETF {
  id?: number;
  basketCurrency?: string;
  asAtDate?: Moment;
  basketInstrument?: string;
  basketInstrumentType?: string;
  basketMinorUnits?: number;
  basketName?: string;
  basketRic?: string;
  basketType?: string;
  family?: string;
  fundCurrency?: string;
  isEtf?: boolean;
  isEtc?: boolean;
}

export class IndexETF implements IIndexETF {
  constructor(
    public id?: number,
    public basketCurrency?: string,
    public asAtDate?: Moment,
    public basketInstrument?: string,
    public basketInstrumentType?: string,
    public basketMinorUnits?: number,
    public basketName?: string,
    public basketRic?: string,
    public basketType?: string,
    public family?: string,
    public fundCurrency?: string,
    public isEtf?: boolean,
    public isEtc?: boolean
  ) {
    this.isEtf = this.isEtf || false;
    this.isEtc = this.isEtc || false;
  }
}
