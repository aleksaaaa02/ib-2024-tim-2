import { Address } from "./address.dto.model";
import {ReviewDTO} from "./review.dto.model";
import {Account} from "../../account/model/account";

export interface AccommodationDetailsDTO {
  id: number;
  name: string;
  description: string;
  reviews: ReviewDTO[];
  avgRating: number;
  filters: string[];
  address: Address;
  owner: Account;
}
