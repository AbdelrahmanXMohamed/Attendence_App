import { DailyStatus } from "./DailyStatus";

export interface StatusRequestModel {
    day: Date;

    userId: number;
    
    status: DailyStatus;
  }