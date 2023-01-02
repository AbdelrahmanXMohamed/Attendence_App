import { DailyStatus } from "./DailyStatus";
import { UserModel } from "./UserModel";

export interface Status {
    id: number;

    day: Date;

    user: UserModel;
    
    status: DailyStatus;
  }
