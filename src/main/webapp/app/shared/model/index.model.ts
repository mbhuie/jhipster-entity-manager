import { Moment } from 'moment';
import { IConstituent } from 'app/shared/model/constituent.model';

export interface IIndex {
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
  constituents?: IConstituent[];
}

export class Index implements IIndex {
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
    public isEtc?: boolean,
    public constituents?: IConstituent[]
  ) {
    this.isEtf = this.isEtf || false;
    this.isEtc = this.isEtc || false;
  }
}
